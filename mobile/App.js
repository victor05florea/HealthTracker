import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import { useEffect, useState } from 'react';

export default function App() {
  const [mesaj, setMesaj] = useState("Se incarca...");

  useEffect(() => {
    fetch('http://10.10.200.2:8080/test') 
      .then(response => response.text()) // Backend-ul trimite text simplu
      .then(data => {
        setMesaj(data); // Salvam mesajul primit
      })
      .catch(error => {
        console.error(error);
        setMesaj("Eroare la conectare: " + error.message);
      });
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Health Tracker App</Text>
      
      <View style={styles.card}>
        <Text style={styles.label}>Mesaj din Java:</Text>
        <Text style={styles.backendMessage}>{mesaj}</Text>
      </View>

      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f0f2f5',
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    color: '#333',
  },
  card: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 5,
  },
  label: {
    fontSize: 16,
    color: '#666',
    marginBottom: 5,
  },
  backendMessage: {
    fontSize: 18,
    color: '#007AFF', // Albastru
    fontWeight: '600',
  }
});