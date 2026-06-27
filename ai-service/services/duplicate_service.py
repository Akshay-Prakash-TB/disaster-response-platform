from models.embedding_model import similarity

THRESHOLD = 0.70


def check_duplicate(request):

    new_text = (
        request.title.strip()
        + ". "
        + request.description.strip()
    )

    matches = []

    for incident in request.existingIncidents:

        existing_text = (
            incident.title.strip()
            + ". "
            + incident.description.strip()
        )

        score = similarity(
            new_text,
            existing_text
        )

        matches.append({

            "incidentId": incident.id,

            "similarityScore": round(
                score,
                2
            )

        })

    matches.sort(

        key=lambda x:
        x["similarityScore"],

        reverse=True

    )

    best = matches[0] if matches else None

    if best and best["similarityScore"] >= THRESHOLD:

        return {

            "duplicateFound": True,

            "bestMatchIncidentId":
            best["incidentId"],

            "bestSimilarityScore":
            best["similarityScore"],

            "matches":
            matches[:3],

            "message":
            "Possible duplicate incident detected."

        }

    return {

        "duplicateFound": False,

        "bestMatchIncidentId": None,

        "bestSimilarityScore": 0.0,

        "matches": matches[:3],

        "message":
        "No duplicate incident found."

    }