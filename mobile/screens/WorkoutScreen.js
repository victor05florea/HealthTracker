import { View, Text, StyleSheet, FlatList, TouchableOpacity, Modal, SafeAreaView, Alert } from 'react-native';
import { useState, useEffect } from 'react';
import Ionicons from '@expo/vector-icons/Ionicons';
import { useIsFocused } from '@react-navigation/native';

export default function WorkoutScreen({ navigation }) {
  const [workouts, setWorkouts] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const isFocused = useIsFocused();

  //Lista de optiuni predefinite
  const workoutTypes = [
    "Spate + Piept",
    "Umeri + Biceps + Triceps",
    "Picioare + Abdomen",
    "Cardio",
    "Full Body"
  ];

  //Functia de incarcare a istoricului
  const fetchWorkouts = () => {
    fetch('http://192.168.1.134:8080/api/workouts')
      .then(res => res.json())
      .then(data => setWorkouts(data.reverse())) 
      .catch(err => console.error("Eroare fetch:", err));
  };

  //Se executa cand intram pe ecran (ca sa vedem update-uri daca stergem/adaugam)
  useEffect(() => {
    if (isFocused) {
      fetchWorkouts();
    }
  }, [isFocused]);

  //Functia de salvare a unui antrenament nou
  const handleAddWorkout = (type) => {
    fetch('http://192.168.1.134:8080/api/workouts', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ type: type })
    })
    .then(res => res.json())
    .then(savedWorkout => {
      setWorkouts([savedWorkout, ...workouts]);
      setModalVisible(false);
    })
    .catch(err => console.error(err));
  };

  //Functia de STERGERE
  const handleDelete = (id) => {
    Alert.alert(
      "Șterge Antrenament",
      "Sigur vrei să ștergi acest antrenament și exercițiile lui?",
      [
        { text: "Nu", style: "cancel" },
        { 
          text: "Da", 
          style: "destructive",
          onPress: () => {
            fetch(`http://192.168.1.134:8080/api/workouts/${id}`, { method: 'DELETE' })
              .then(() => {
                // Scoatem elementul din lista locala ca sa nu mai facem request la server
                setWorkouts(prevWorkouts => prevWorkouts.filter(item => item.id !== id));
              })
              .catch(err => console.error("Eroare stergere:", err));
          }
        }
      ]
    );
  };

  const renderItem = ({ item }) => (
    <TouchableOpacity 
      style={styles.card}
      //navigarea catre detalii
      onPress={() => navigation.navigate('WorkoutDetail', { workout: item })}
    >
      <View style={styles.iconContainer}>
        <Ionicons name="barbell" size={24} color="white" />
      </View>
      
      <View style={{ flex: 1 }}>
        <Text style={styles.workoutType}>{item.type}</Text>
        <Text style={styles.date}>
          {new Date(item.date).toLocaleDateString('ro-RO', { day: 'numeric', month: 'long', hour: '2-digit', minute:'2-digit' })}
        </Text>
      </View>

      {/* Butonul de Stergere (Cos de gunoi) */}
      <TouchableOpacity 
        onPress={() => handleDelete(item.id)} 
        style={styles.deleteButton}
      >
        <Ionicons name="trash-outline" size={22} color="#e17055" />
      </TouchableOpacity>
    </TouchableOpacity>
  );

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Antrenamente</Text>

      <FlatList
        data={workouts}
        renderItem={renderItem}
        keyExtractor={item => item.id.toString()}
        contentContainerStyle={styles.list}
        ListEmptyComponent={<Text style={styles.emptyText}>Nu ai niciun antrenament încă.</Text>}
      />

      <TouchableOpacity 
        style={styles.addButton} 
        onPress={() => setModalVisible(true)}
      >
        <Ionicons name="add" size={32} color="white" />
      </TouchableOpacity>

      {/* Modalul a ramas la fel */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Alege Antrenamentul</Text>
            {workoutTypes.map((type, index) => (
              <TouchableOpacity 
                key={index} 
                style={styles.optionButton}
                onPress={() => handleAddWorkout(type)}
              >
                <Text style={styles.optionText}>{type}</Text>
              </TouchableOpacity>
            ))}
            <TouchableOpacity 
              style={styles.cancelButton}
              onPress={() => setModalVisible(false)}
            >
              <Text style={styles.cancelText}>Anulează</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f7fa' },
  title: { fontSize: 28, fontWeight: 'bold', textAlign: 'center', marginVertical: 20, color: '#2d3436' },
  list: { paddingHorizontal: 20, paddingBottom: 100 },
  card: { flexDirection: 'row', alignItems: 'center', backgroundColor: 'white', borderRadius: 15, padding: 15, marginBottom: 15, elevation: 3 },
  iconContainer: { backgroundColor: '#6c5ce7', padding: 10, borderRadius: 10, marginRight: 15 },
  workoutType: { fontSize: 18, fontWeight: 'bold', color: '#2d3436' },
  date: { color: 'gray', fontSize: 14 },
  deleteButton: { padding: 10 },
  addButton: { position: 'absolute', bottom: 30, right: 30, backgroundColor: '#0984e3', width: 60, height: 60, borderRadius: 30, justifyContent: 'center', alignItems: 'center', elevation: 8 },
  modalOverlay: { flex: 1, justifyContent: 'flex-end', backgroundColor: 'rgba(0,0,0,0.5)' },
  modalContent: { backgroundColor: 'white', borderTopLeftRadius: 20, borderTopRightRadius: 20, padding: 20 },
  modalTitle: { fontSize: 20, fontWeight: 'bold', textAlign: 'center', marginBottom: 20 },
  optionButton: { backgroundColor: '#f1f2f6', padding: 15, borderRadius: 10, marginBottom: 10 },
  optionText: { fontSize: 16, textAlign: 'center', fontWeight: '600', color: '#2d3436' },
  cancelButton: { marginTop: 10, padding: 15 },
  cancelText: { textAlign: 'center', color: 'red', fontWeight: 'bold' },
  emptyText: { textAlign: 'center', marginTop: 50, color: 'gray' }
});