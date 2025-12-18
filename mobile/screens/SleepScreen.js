import { StyleSheet, Text, View, FlatList, SafeAreaView } from 'react-native';
import { useEffect, useState } from 'react';

export default function SleepScreen() {
  const [sleepData, setSleepData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://192.168.1.134:8080/api/sleep') 
      .then(response => response.json())
      .then(data => {
        setSleepData(data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Eroare:", error);
        setLoading(false);
      });
  }, []);

  const formatTime = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleString('ro-RO', { hour: '2-digit', minute: '2-digit', day: 'numeric', month: 'short' });
  };

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
      <Text style={styles.title}>Istoricul Somnului</Text>
      {loading ? (
        <Text style={styles.loading}>Se încarcă datele...</Text>
      ) : (
        <FlatList
          data={sleepData}
          renderItem={renderItem}
          keyExtractor={item => item.id.toString()}
          contentContainerStyle={styles.list}
          ListEmptyComponent={<Text style={styles.loading}>Nu exista date.</Text>}
        />
      )}
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f7fa' },
  title: { fontSize: 28, fontWeight: 'bold', color: '#2d3436', textAlign: 'center', marginVertical: 20 },
  list: { paddingHorizontal: 20, paddingBottom: 20 },
  loading: { textAlign: 'center', fontSize: 18, color: '#636e72', marginTop: 50 },
  card: { backgroundColor: 'white', borderRadius: 15, padding: 20, marginBottom: 15, shadowColor: '#000', shadowOffset: { width: 0, height: 4 }, shadowOpacity: 0.1, shadowRadius: 5, elevation: 5 },
  row: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  separator: { height: 1, backgroundColor: '#dfe6e9', marginVertical: 10 },
  label: { fontSize: 16, color: '#636e72', fontWeight: '500' },
  time: { fontSize: 18, color: '#2d3436', fontWeight: 'bold' }
});