import React, { useState, useRef } from 'react';
import { 
  View, 
  Text, 
  StyleSheet, 
  TextInput, 
  TouchableOpacity, 
  FlatList, 
  SafeAreaView, 
  KeyboardAvoidingView, 
  Platform,
  ActivityIndicator 
} from 'react-native';
import Ionicons from '@expo/vector-icons/Ionicons';

export default function ChatScreen() {
  const [messages, setMessages] = useState([
    { id: '1', text: 'Salut! Sunt antrenorul tău AI. Cu ce te pot ajuta azi?', sender: 'ai' }
  ]);
  const [inputText, setInputText] = useState('');
  const [loading, setLoading] = useState(false);
  const flatListRef = useRef();

  const sendMessage = () => {
    if (!inputText.trim()) return;
    //Adaugam mesajul in lista
    const userMsg = { id: Date.now().toString(), text: inputText, sender: 'user' };
    setMessages(prev => [...prev, userMsg]);
    setInputText('');
    setLoading(true);

    //Scroll jos automat
    setTimeout(() => flatListRef.current?.scrollToEnd(), 100);

    //Trimitem la Backend
    fetch('http://192.168.1.134:8080/api/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ message: userMsg.text })
    })
    .then(res => res.json())
    .then(data => {
      //Adaugam raspunsul
      const aiMsg = { id: Date.now().toString() + '_ai', text: data.reply, sender: 'ai' };
      setMessages(prev => [...prev, aiMsg]);
      setLoading(false);
      setTimeout(() => flatListRef.current?.scrollToEnd(), 100);
    })
    .catch(err => {
      console.error(err);
      setLoading(false);
      const errorMsg = { id: Date.now().toString(), text: "Eroare conexiune server.", sender: 'ai' };
      setMessages(prev => [...prev, errorMsg]);
    });
  };

  const renderItem = ({ item }) => {
    const isUser = item.sender === 'user';
    return (
      <View style={[
        styles.messageBubble, 
        isUser ? styles.userBubble : styles.aiBubble
      ]}>
        <Text style={[styles.messageText, isUser ? styles.userText : styles.aiText]}>
          {item.text}
        </Text>
      </View>
    );
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>ChatBot</Text>
      </View>

      <FlatList
        ref={flatListRef}
        data={messages}
        renderItem={renderItem}
        keyExtractor={item => item.id}
        contentContainerStyle={styles.listContent}
      />

      {loading && (
        <View style={{ padding: 10 }}>
          <Text style={{ textAlign: 'center', color: 'gray' }}>ChatBot-ul scrie...</Text>
          <ActivityIndicator size="small" color="#0984e3" />
        </View>
      )}

      <KeyboardAvoidingView 
        behavior={Platform.OS === "ios" ? "padding" : "height"} 
        keyboardVerticalOffset={Platform.OS === "ios" ? 90 : 0}
        style={styles.inputContainer}
      >
        <TextInput
          style={styles.input}
          value={inputText}
          onChangeText={setInputText}
          placeholder="Întreabă ceva..."
          placeholderTextColor="#999"
        />
        <TouchableOpacity style={styles.sendButton} onPress={sendMessage}>
          <Ionicons name="send" size={20} color="white" />
        </TouchableOpacity>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f7fa' },
  header: { padding: 15, backgroundColor: 'white', borderBottomWidth: 1, borderBottomColor: '#eee', alignItems: 'center' },
  headerTitle: { fontSize: 18, fontWeight: 'bold', color: '#2d3436' },
  listContent: { padding: 15, paddingBottom: 20 },
  
  messageBubble: { maxWidth: '80%', padding: 12, borderRadius: 20, marginBottom: 10 },
  userBubble: { alignSelf: 'flex-end', backgroundColor: '#0984e3', borderBottomRightRadius: 2 },
  aiBubble: { alignSelf: 'flex-start', backgroundColor: 'white', borderBottomLeftRadius: 2, borderWidth: 1, borderColor: '#eee' },
  
  messageText: { fontSize: 16 },
  userText: { color: 'white' },
  aiText: { color: '#2d3436' },

  inputContainer: { flexDirection: 'row', padding: 10, backgroundColor: 'white', alignItems: 'center', borderTopWidth: 1, borderTopColor: '#eee' },
  input: { flex: 1, backgroundColor: '#f1f2f6', borderRadius: 20, paddingHorizontal: 15, paddingVertical: 10, fontSize: 16, marginRight: 10 },
  sendButton: { backgroundColor: '#0984e3', width: 45, height: 45, borderRadius: 25, justifyContent: 'center', alignItems: 'center' }
});