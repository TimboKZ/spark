// Define pins for each colour
#define RED 2
#define GREEN 3
#define BLUE 4

#define RATIO 7.0833333

// Buffer for serial data
char serial[50];

// Memory for colours from previous frame;
int memory[3];

void setup() {
  // Initialise all colour pins
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);

  // Begin USB serial communication
  Serial.begin(9600);
}

int constraint(int max, int min, int num) {
  if(num > max) return max;
  if(num < min) return min;
  return num;
}

double toDecimal(char c) {
  double code = (int) c;
  if(code < 60) {
    return code - 48;
  } else {
    return code - 97 + 10;
  }
}

void updateRGB(int r, int g, int b) {
  analogWrite(RED, constraint(255, 0, r));
  analogWrite(GREEN, constraint(255, 0, g));
  analogWrite(BLUE, constraint(255, 0, b));
}

int numFromSerial(int start) {
  char c[4];
  int i;
  for(i = 0; i < 3; i++) {
    c[i] = serial[start + i];
  }
  c[i] = '\0';
  return atoi(c);
}

void loop() {

  int k = 0;
  char save = 'o';
  int update = 0;
  while(Serial.available()) {
    update = 1;
    serial[k++] = Serial.read();
  }
  serial[k] = '\0';

  if(update) {
    int red = (int) (toDecimal(serial[0]) * RATIO);
    int green = (int) (toDecimal(serial[1]) * RATIO);
    int blue = (int) (toDecimal(serial[2]) * RATIO);
    memory[0] += (red - memory[0]) / 1;
    memory[1] += (green - memory[1]) / 1;
    memory[2] += (blue - memory[2]) / 1;
    updateRGB(memory[0], memory[1], memory[2]);
  }

  delay(50);
}
