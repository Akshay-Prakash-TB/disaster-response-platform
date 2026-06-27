from pydantic import BaseModel


class ExistingIncident(BaseModel):

    id: int

    title: str

    description: str


class DuplicateRequest(BaseModel):

    title: str

    description: str

    existingIncidents: list[ExistingIncident]