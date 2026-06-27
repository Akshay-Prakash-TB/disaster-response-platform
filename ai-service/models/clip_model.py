from transformers import CLIPProcessor, CLIPModel
from PIL import Image
import torch

from config.labels import DISASTER_LABELS
from models.yolo_model import detect_objects

print("Loading CLIP model...")

model = CLIPModel.from_pretrained(
    "openai/clip-vit-base-patch32"
)

processor = CLIPProcessor.from_pretrained(
    "openai/clip-vit-base-patch32"
)

print("CLIP Loaded")


def create_summary(disaster, objects):

    summary = []

    disaster_name = disaster.replace(" disaster", "").title()

    summary.append(
        f"The uploaded image most likely shows a {disaster_name.lower()}."
    )

    if "person" in objects:

        if objects["person"] == 1:

            summary.append(
                "One civilian is visible."
            )

        else:

            summary.append(
                f"{objects['person']} civilians are visible."
            )

    vehicles = []

    for vehicle in [
        "car",
        "truck",
        "bus",
        "motorcycle",
        "bicycle",
        "boat"
    ]:

        if vehicle in objects:

            vehicles.append(
                f"{objects[vehicle]} {vehicle}"
            )

    if len(vehicles) > 0:

        summary.append(

            "Visible vehicles include "
            + ", ".join(vehicles)
            + "."

        )

    if "fire hydrant" in objects:

        summary.append(
            "Emergency infrastructure is visible."
        )

    if disaster_name == "Flood":

        summary.append(
            "Flood conditions may disrupt transportation and require rescue operations."
        )

    elif disaster_name == "Fire":

        summary.append(
            "Fire conditions may require immediate emergency response."
        )

    elif disaster_name == "Road Accident":

        summary.append(
            "The scene indicates a possible traffic incident requiring assessment."
        )

    elif disaster_name == "Building Collapse":

        summary.append(
            "Structural damage appears likely and search-and-rescue may be required."
        )

    elif disaster_name == "Landslide":

        summary.append(
            "Terrain obstruction may affect nearby transportation routes."
        )

    elif disaster_name == "Earthquake Damage":

        summary.append(
            "Infrastructure inspection is recommended."
        )

    return " ".join(summary)


def analyze(image_path):

    image = Image.open(image_path).convert("RGB")

    inputs = processor(
        text=DISASTER_LABELS,
        images=image,
        return_tensors="pt",
        padding=True
    )

    outputs = model(**inputs)

    probabilities = outputs.logits_per_image.softmax(dim=1)

    index = torch.argmax(probabilities).item()

    confidence = probabilities[0][index].item() * 100

    objects = detect_objects(image_path)

    return {

        "disasterType":
        DISASTER_LABELS[index],

        "confidence":
        round(confidence, 2),

        "objects":
        objects,

        "summary":
        create_summary(
            DISASTER_LABELS[index],
            objects
        )

    }