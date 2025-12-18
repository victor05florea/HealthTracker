import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { StatusBar } from 'expo-status-bar';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import WorkoutDetailScreen from './screens/WorkoutDetailScreen';

//Importez ecranele
import ChatScreen from './screens/ChatScreen';
import SleepScreen from './screens/SleepScreen';
import WorkoutScreen from './screens/WorkoutScreen';
import StepsScreen from './screens/StepsScreen';

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

function WorkoutStack() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Înapoi" component={WorkoutScreen} options={{ headerShown: false }} />
      <Stack.Screen name="WorkoutDetail" component={WorkoutDetailScreen} options={{ title: 'Detalii Antrenament' }} />
    </Stack.Navigator>
  );
}

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          headerShown: false,
          tabBarIcon: ({ focused, color, size }) => {
            let iconName;

            if (route.name === 'Somn') {
              iconName = focused ? 'moon' : 'moon-outline';
            } else if (route.name === 'Workout') {
              iconName = focused ? 'barbell' : 'barbell-outline';
            } else if (route.name === 'Pasi') {
              iconName = focused ? 'walk' : 'walk-outline';
            } else if (route.name === 'ChatBot') {
              iconName = focused ? 'chatbubbles' : 'chatbubbles-outline';
            }

            return <Ionicons name={iconName} size={size} color={color} />;
          },
          tabBarActiveTintColor: '#007AFF',
          tabBarInactiveTintColor: 'gray',
        })}
      >
        <Tab.Screen name="Pasi" component={StepsScreen} />
        <Tab.Screen name="Somn" component={SleepScreen} />
        <Tab.Screen name="Workout" component={WorkoutStack} />
        <Tab.Screen name="ChatBot" component={ChatScreen} />
      </Tab.Navigator>
      <StatusBar style="auto" />
    </NavigationContainer>
  );
}