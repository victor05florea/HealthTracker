import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, SafeAreaView, TouchableOpacity, Alert } from 'react-native';
import { Pedometer } from 'expo-sensors';
import Ionicons from '@expo/vector-icons/Ionicons';
import { useIsFocused } from '@react-navigation/native'; // Ca sa actualizam cand revenim pe ecran

export default function StepsScreen() {
  const [isPedometerAvailable, setIsPedometerAvailable] = useState('checking');
  const [currentStepCount, setCurrentStepCount] = useState(0);
  const isFocused = useIsFocused(); // True cand suntem pe acest ecran
  
  const DAILY_GOAL = 6000;

  useEffect(() => {
    let subscription;

    const startTracking = async () => {
      // 1. Verificam senzorul
      const isAvailable = await Pedometer.isAvailableAsync();
      setIsPedometerAvailable(String(isAvailable));

      if (isAvailable) {
        // 2. IMPORTANT: Citim istoricul de la miezul noptii pana ACUM
        const end = new Date();
        const start = new Date();
        start.setHours(0, 0, 0, 0); // Azi la ora 00:00

        try {
          const pastStepCountResult = await Pedometer.getStepCountAsync(start, end);
          if (pastStepCountResult) {
            setCurrentStepCount(pastStepCountResult.steps);
          }
        } catch (error) {
          console.log("Nu am putut citi istoricul (poate lipsesc permisiunile):", error);
        }

        // 3. Ne abonam la pasii LIVE (ca sa creasca numarul in timp ce mergi)
        subscription = Pedometer.watchStepCount(result => {
            // Cand mergi, adunam pasii noi la ce aveam deja?
            // Din pacate watchStepCount pe unele telefoane da totalul, pe altele doar delta.
            // Cea mai sigura metoda: Cand detectam miscare, recitim totalul de la 00:00
            
            // Recitim totalul zilei pentru precizie maxima
            const now = new Date();
            const midnight = new Date();
            midnight.setHours(0, 0, 0, 0);
            
            Pedometer.getStepCountAsync(midnight, now).then(res => {
                 setCurrentStepCount(res.steps);
            });
        });
      }
    };

    if (isFocused) {
        startTracking();
    }

    // Curatenie cand iesim de pe ecran
    return () => {
      if (subscription) {
        subscription.remove();
      }
    };
  }, [isFocused]); // Se re-executa cand intri pe ecran

  // Functia de salvare ramane la fel
  const syncSteps = () => {
    // ATENTIE: Verifica IP-ul!
    fetch('http://10.10.200.2:8080/api/steps', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(currentStepCount)
    })
    .then(res => res.json())
    .then(data => {
      Alert.alert("Succes", `Am salvat ${data.steps} pași în baza de date!`);
    })
    .catch(err => console.error(err));
  };

  const progress = Math.min(currentStepCount / DAILY_GOAL, 1);

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Pași Azi 👣</Text>

      <View style={styles.circleContainer}>
        <View style={[styles.circle, { borderColor: progress >= 1 ? '#00b894' : '#0984e3' }]}> 
          <Text style={styles.stepCount}>{currentStepCount}</Text>
          <Text style={styles.stepLabel}>pași</Text>
        </View>
      </View>

      <Text style={styles.goalText}>Ținta: {DAILY_GOAL}</Text>
      
      <Text style={styles.status}>
        Senzor: {isPedometerAvailable === 'true' ? 'Activ ✅' : 'Verifică permisiunile ⚠️'}
      </Text>

      <TouchableOpacity style={styles.syncButton} onPress={syncSteps}>
        <Ionicons name="cloud-upload-outline" size={24} color="white" />
        <Text style={styles.syncText}>Salvează Progresul</Text>
      </TouchableOpacity>

    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f7fa', alignItems: 'center', paddingTop: 50 },
  title: { fontSize: 28, fontWeight: 'bold', color: '#2d3436', marginBottom: 40 },
  circleContainer: { marginBottom: 30 },
  circle: {
    width: 200, height: 200, borderRadius: 100, borderWidth: 10,
    justifyContent: 'center', alignItems: 'center', backgroundColor: 'white', elevation: 5
  },
  stepCount: { fontSize: 48, fontWeight: 'bold', color: '#2d3436' },
  stepLabel: { fontSize: 18, color: 'gray' },
  goalText: { fontSize: 18, color: '#636e72', marginBottom: 10 },
  status: { fontSize: 14, color: '#b2bec3', marginBottom: 40 },
  syncButton: { 
    flexDirection: 'row', backgroundColor: '#0984e3', paddingVertical: 15, paddingHorizontal: 30, 
    borderRadius: 30, alignItems: 'center', elevation: 5 
  },
  syncText: { color: 'white', fontSize: 18, fontWeight: 'bold', marginLeft: 10 }
});