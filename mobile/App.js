import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { StatusBar } from 'expo-status-bar';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import WorkoutDetailScreen from './screens/WorkoutDetailScreen';

// Importam ecranele noastre
import SleepScreen from './screens/SleepScreen';
import WorkoutScreen from './screens/WorkoutScreen';
import StepsScreen from './screens/StepsScreen';

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

function WorkoutStack() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="WorkoutList" component={WorkoutScreen} options={{ headerShown: false }} />
      <Stack.Screen name="WorkoutDetail" component={WorkoutDetailScreen} options={{ title: 'Detalii Antrenament' }} />
    </Stack.Navigator>
  );
}

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          headerShown: false, // Ascundem bara de sus standard (avem titlurile noastre)
          tabBarIcon: ({ focused, color, size }) => {
            let iconName;

            if (route.name === 'Somn') {
              iconName = focused ? 'moon' : 'moon-outline';
            } else if (route.name === 'Sport') {
              iconName = focused ? 'barbell' : 'barbell-outline';
            } else if (route.name === 'Pasi') {
              iconName = focused ? 'walk' : 'walk-outline';
            }

            return <Ionicons name={iconName} size={size} color={color} />;
          },
          tabBarActiveTintColor: '#007AFF', // Culoarea cand e selectat (Albastru)
          tabBarInactiveTintColor: 'gray',
        })}
      >
        <Tab.Screen name="Somn" component={SleepScreen} />
        <Tab.Screen name="Sport" component={WorkoutStack} />
        <Tab.Screen name="Pasi" component={StepsScreen} />
      </Tab.Navigator>
      <StatusBar style="auto" />
    </NavigationContainer>
  );
}