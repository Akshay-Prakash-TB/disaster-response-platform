from pydantic import BaseModel


class DuplicateMatch(BaseModel):

    incidentId: int

    similarityScore: float


class DuplicateResponse(BaseModel):

    duplicateFound: bool

    bestMatchIncidentId: int | None

    bestSimilarityScore: float

    matches: list[DuplicateMatch]

    message: str