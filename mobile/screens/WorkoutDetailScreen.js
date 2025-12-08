import { View, Text, StyleSheet, FlatList, TextInput, TouchableOpacity, KeyboardAvoidingView, Platform } from 'react-native';
import { useState } from 'react';
import Ionicons from '@expo/vector-icons/Ionicons';

export default function WorkoutDetailScreen({ route }) {
  // Primim datele antrenamentului pe care am dat click
  const { workout } = route.params; 
  
  // Lista de exercitii (incepe cu ce avem deja in baza de date)
  const [exercises, setExercises] = useState(workout.exercises || []);
  
  // State pentru formularul de adaugare
  const [name, setName] = useState('');
  const [sets, setSets] = useState('');
  const [reps, setReps] = useState('');
  const [weight, setWeight] = useState('');

  const handleAddExercise = () => {
    if (!name || !sets || !reps || !weight) return;

    const newExercise = {
      name,
      sets: parseInt(sets),
      reps: parseInt(reps),
      weight: parseFloat(weight)
    };

    // ATENTIE: Verifica IP-ul aici!
    fetch(`http://10.10.200.2:8080/api/workouts/${workout.id}/exercises`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newExercise)
    })
    .then(res => res.json())
    .then(savedExercise => {
      // Adaugam exercitiul in lista de pe ecran
      setExercises([...exercises, savedExercise]);
      // Golim casutele
      setName(''); setSets(''); setReps(''); setWeight('');
    })
    .catch(err => console.error(err));
  };

  return (
    <KeyboardAvoidingView 
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      style={styles.container}
    >
      <View style={styles.header}>
        <Text style={styles.title}>{workout.type}</Text>
        <Text style={styles.subtitle}>
            Data: {new Date(workout.date).toLocaleDateString('ro-RO')}
        </Text>
      </View>

      {/* Lista de exercitii */}
      <FlatList
        data={exercises}
        // MODIFICAREA E AICI:
        // Daca are ID, il folosim. Daca nu, generam un text unic ("new-0", "new-1")
        // Asta previne conflictul dintre ID-ul 1 din baza de date si indexul 1 din lista
        keyExtractor={(item, index) => item.id ? item.id.toString() : `new-${index}`}
        renderItem={({ item }) => (
          <View style={styles.exerciseCard}>
            <View>
                <Text style={styles.exerciseName}>{item.name}</Text>
                <Text style={styles.exerciseDetails}>
                {item.sets} serii x {item.reps} rep
                </Text>
            </View>
            <View style={styles.weightBadge}>
                <Text style={styles.weightText}>{item.weight} kg</Text>
            </View>
          </View>
        )}
        ListEmptyComponent={<Text style={styles.empty}>Nu ai adăugat exerciții încă.</Text>}
        contentContainerStyle={{ paddingBottom: 20 }}
      />

      {/* Formular Adaugare (Josul ecranului) */}
      <View style={styles.form}>
        <Text style={styles.formTitle}>Adaugă Exercițiu</Text>
        <TextInput 
            placeholder="Nume Exercițiu (ex: Împins piept)" 
            style={styles.input} 
            value={name} 
            onChangeText={setName} 
        />
        
        <View style={styles.row}>
          <TextInput placeholder="Serii" keyboardType="numeric" style={[styles.input, styles.smallInput]} value={sets} onChangeText={setSets} />
          <TextInput placeholder="Repetări" keyboardType="numeric" style={[styles.input, styles.smallInput]} value={reps} onChangeText={setReps} />
          <TextInput placeholder="KG" keyboardType="numeric" style={[styles.input, styles.smallInput]} value={weight} onChangeText={setWeight} />
        </View>

        <TouchableOpacity style={styles.addButton} onPress={handleAddExercise}>
          <Text style={styles.addButtonText}>Salvează</Text>
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f7fa' },
  header: { padding: 20, backgroundColor: 'white', borderBottomWidth: 1, borderBottomColor: '#eee' },
  title: { fontSize: 22, fontWeight: 'bold', color: '#2d3436' },
  subtitle: { color: 'gray', marginTop: 5 },
  
  exerciseCard: { 
      backgroundColor: 'white', 
      padding: 15, 
      marginHorizontal: 15, 
      marginTop: 10, 
      borderRadius: 12, 
      flexDirection: 'row', 
      justifyContent: 'space-between',
      alignItems: 'center',
      elevation: 2 
  },
  exerciseName: { fontSize: 16, fontWeight: 'bold', color: '#333' },
  exerciseDetails: { fontSize: 14, color: 'gray', marginTop: 2 },
  weightBadge: { backgroundColor: '#e0f7fa', paddingHorizontal: 10, paddingVertical: 5, borderRadius: 8 },
  weightText: { color: '#006064', fontWeight: 'bold' },

  empty: { textAlign: 'center', marginTop: 40, color: 'gray' },
  
  form: { backgroundColor: 'white', padding: 20, borderTopLeftRadius: 20, borderTopRightRadius: 20, elevation: 20, shadowColor: '#000', shadowOpacity: 0.1, shadowRadius: 10 },
  formTitle: { fontSize: 18, fontWeight: 'bold', marginBottom: 15, color: '#2d3436' },
  input: { backgroundColor: '#f1f2f6', padding: 12, borderRadius: 8, marginBottom: 10, fontSize: 16 },
  row: { flexDirection: 'row', justifyContent: 'space-between' },
  smallInput: { width: '30%' },
  addButton: { backgroundColor: '#6c5ce7', padding: 15, borderRadius: 10, alignItems: 'center', marginTop: 5 },
  addButtonText: { color: 'white', fontWeight: 'bold', fontSize: 16 }
});