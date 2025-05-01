import base64
import pickle
import hashlib

 # Codifica os dados como string base64

def generate_seed(input):
    dados_64 = base64.b64encode(pickle.dumps(input)).decode("utf-8")

    encrypted = hashlib.sha256(dados_64.encode()).hexdigest()
    return encrypted