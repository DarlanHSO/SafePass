import requests
import json
from generate_seed import generate_seed

def call_api():
    api_key = "def0ef36-4f3d-494d-9a4c-0ee79b0f91d1"

    # URL do endpoint
    url = 'https://api.random.org/json-rpc/4/invoke'

    # Corpo da requisição
    headers = {'Content-Type': 'application/json'}

    payload = {
        "jsonrpc": "2.0",
        "method": "generateStrings",
        "params": {
            "apiKey": api_key,
            "n": 1,  #Number of strings requested
            "length": 32,
            "characters": "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
            "replacement": True
        },
        "id": 1
    }

    response = requests.post(url, headers=headers, data=json.dumps(payload))

    # Codifica os dados como string base64
    seed = generate_seed(response)
    return seed