import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, FlatList, SafeAreaView } from 'react-native';
import { useEffect, useState } from 'react';

export default function App() {
  const [sleepData, setSleepData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://10.10.200.2:8080/api/sleep')
      .then(response => response.json()) // Acum asteptam JSON (lista), nu text
      .then(data => {
        setSleepData(data); // Salvam lista in starea aplicatiei
        setLoading(false);
      })
      .catch(error => {
        console.error("Eroare:", error);
        setLoading(false);
      });
  }, []);

  // Functie simpla pentru a arata data frumos (fara secunde si milisecunde)
  const formatTime = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleString('ro-RO', { 
      hour: '2-digit', 
      minute: '2-digit',
      day: 'numeric',
      month: 'short' 
    });
  };

  // Cum arata un singur rand din lista (un Card)
  const renderItem = ({ item }) => (
    <View style={styles.card}>
      <View style={styles.row}>
        <Text style={styles.label}>Culcare:</Text>
        <Text style={styles.time}>{formatTime(item.startTime)}</Text>
      </View>
      <View style={styles.separator} />
      <View style={styles.row}>
        <Text style={styles.label}>Trezire:</Text>
        <Text style={styles.time}>{formatTime(item.endTime)}</Text>
      </View>
    </View>
  );

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Istoric Somn 🌙</Text>
      
      {loading ? (
        <Text style={styles.loading}>Se încarcă datele...</Text>
      ) : (
        <FlatList
          data={sleepData}
          renderItem={renderItem}
          keyExtractor={item => item.id.toString()}
          contentContainerStyle={styles.list}
          ListEmptyComponent={<Text>Nu exista date.</Text>}
        />
      )}
      
      <StatusBar style="auto" />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f7fa',
    paddingTop: 50, // Spatiu sus pentru telefoanele cu notch
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2d3436',
    textAlign: 'center',
    marginBottom: 20,
  },
  list: {
    paddingHorizontal: 20,
    paddingBottom: 20,
  },
  loading: {
    textAlign: 'center',
    fontSize: 18,
    color: '#636e72',
    marginTop: 50,
  },
  card: {
    backgroundColor: 'white',
    borderRadius: 15,
    padding: 20,
    marginBottom: 15,
    // Umbra pentru efect 3D
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 5, // Umbra pentru Android
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  separator: {
    height: 1,
    backgroundColor: '#dfe6e9',
    marginVertical: 10,
  },
  label: {
    fontSize: 16,
    color: '#636e72',
    fontWeight: '500',
  },
  time: {
    fontSize: 18,
    color: '#2d3436',
    fontWeight: 'bold',
  }
});