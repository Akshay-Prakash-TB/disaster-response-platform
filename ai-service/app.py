from fastapi import FastAPI, UploadFile, File
import shutil
import os

from models.clip_model import analyze
from models.duplicate_request import DuplicateRequest
from services.duplicate_service import check_duplicate

app = FastAPI()


@app.post("/analyze-image")
async def analyze_image(
        image: UploadFile = File(...)):

    os.makedirs(
        "temp",
        exist_ok=True
    )

    temp_path = os.path.join(
        "temp",
        image.filename
    )

    with open(
            temp_path,
            "wb") as buffer:

        shutil.copyfileobj(
            image.file,
            buffer
        )

    result = analyze(
        temp_path
    )

    os.remove(
        temp_path
    )

    return result

@app.post("/check-duplicate")
def check_duplicate_endpoint(
    request: DuplicateRequest
):

    return check_duplicate(request)