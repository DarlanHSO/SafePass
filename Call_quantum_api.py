import requests
from generate_seed import generate_seed

def call_api():
    url = "https://qrng.anu.edu.au/API/jsonI.php"
    api_key = "1DcUeudpLc20MCkBqxkJXmiZXRSd5qqaCCpKAmM9"

    params = {
        "length": 15,
        "type": "hex8",
        "size": 2,
        "apiKey": api_key
    }

    try:
        response = requests.get(url, params=params, timeout=5)
        response.raise_for_status()
    except requests.RequestException as e:
        print("Erro ao acessar ANU Quantum API:", e)
        return None

    seed = generate_seed(response)
    return seed
