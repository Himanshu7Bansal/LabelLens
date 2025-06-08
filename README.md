# LabelLens 📷🔍

An Android application that uses machine learning to detect and identify objects in images with
real-time bounding box visualization.

## ✨ Features

- **Image Selection**: Pick images from device gallery using modern photo picker
- **Object Detection**: Powered by Google ML Kit Vision API for accurate object recognition
- **Visual Feedback**: Real-time bounding boxes with confidence-based color coding
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Multiple Objects**: Detect and classify multiple objects in a single image

## 🎯 How It Works

1. **Select Image**: Tap "Open Photos" to choose an image from your device
2. **AI Processing**: The app automatically processes the image using ML Kit's object detection
3. **Results Display**: View both the original image and the analyzed version with:
    - Colored bounding boxes around detected objects
    - Object labels with confidence scores
    - Color-coded confidence levels

## 🎨 Confidence Color Coding

- 🟢 **Green**: 85-100% confidence (Highly accurate)
- 🟡 **Yellow**: 70-85% confidence (Good accuracy)
- 🟣 **Magenta**: 50-70% confidence (Moderate accuracy)
- 🔴 **Red**: Below 50% confidence (Low accuracy)

## 🛠️ Tech Stack

### **Language & Framework**

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI toolkit
- **Material 3** - Latest Material Design components

### **Architecture**

- **MVVM Pattern** - Model-View-ViewModel architecture
- **Android ViewModel** - UI-related data holder with lifecycle awareness
- **Compose State Management** - Reactive UI updates

### **Key Dependencies**

```kotlin
// ML Kit Object Detection
dependencies {
  // ...
  implementation("com.google.mlkit:object-detection:17.0.2")
}
```

## 🏗️ Project Structure

```
app/src/main/java/dev/h7b/labelLens/
├── MainActivity.kt              # Main activity with navigation logic
├── MainViewModel.kt             # ViewModel handling ML Kit integration
├── ui/
│   ├── components/
│   │   └── Image.kt            # Custom image components with overlay
│   ├── screen/
│   │   ├── imagePicker/        # Image selection screen
│   │   └── objectIdentifier/   # Results display screen
│   └── theme/                  # App theming
└── extensions/                 # Utility extensions
```

## 🔧 Key Implementation Details

### **Object Detection Setup**

```kotlin
private val options by lazy {
    ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
        .enableClassification()
        .enableMultipleObjects()
        .build()
}
```

### **Image Processing Pipeline**

1. Convert URI to InputImage using ML Kit
2. Process with ObjectDetector
3. Extract bounding boxes and labels
4. Apply confidence-based styling
5. Render overlay on original image

### **Custom Drawing**

- Canvas-based bounding box rendering
- Dynamic scaling based on image dimensions
- Text labels with confidence scores

## 📋 Requirements

- **Android API Level**: 29+ (Android 10)
- **Target SDK**: 35
- **Kotlin**: JVM Target 11
- **Permissions**: Storage access for image selection

## 🚀 Getting Started

1. **Clone the repository**
2. **Open in Android Studio**
3. **Sync Gradle dependencies**
4. **Run on device or emulator**

## 🤖 ML Kit Integration

This app leverages **Google ML Kit's Object Detection API** which provides:

- On-device processing (no internet required)
- Real-time object detection
- Pre-trained models for common objects
- High accuracy with optimized performance
- Multiple object detection in single image
