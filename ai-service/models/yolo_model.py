from ultralytics import YOLO

print("Loading YOLO...")

model = YOLO("yolov8n.pt")

print("YOLO Loaded")


def detect_objects(image_path):

    results = model(image_path)

    detected = {}

    for result in results:

        for box in result.boxes:

            cls = int(box.cls)

            name = model.names[cls]

            if name in detected:
                detected[name] += 1
            else:
                detected[name] = 1

    return detected