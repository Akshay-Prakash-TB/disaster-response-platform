from sentence_transformers import SentenceTransformer
from sentence_transformers.util import cos_sim

print("Loading Sentence Transformer...")

model = SentenceTransformer(
    "all-MiniLM-L6-v2"
)

print("Sentence Transformer Loaded")


def get_embedding(text):

    return model.encode(
        text,
        convert_to_tensor=True
    )


def similarity(text1, text2):

    embedding1 = get_embedding(text1)

    embedding2 = get_embedding(text2)

    score = cos_sim(
        embedding1,
        embedding2
    )

    return float(score)