import random
import requests
import time
from generate_seed import generate_seed

api_key = "cd940c5a57424e03a11193341250604"

def gerar_coordenadas():
    lat = round(random.uniform(-90, 90), 4)
    lon = round(random.uniform(-180, 180), 4)
    return lat, lon

def buscar_clima(api_key):
    while True:
        lat, lon = gerar_coordenadas()
        location = f"{lat},{lon}"
        url = f"http://api.weatherapi.com/v1/current.json?key={api_key}&q={location}&aqi=no"

        response = requests.get(url)

        if response.status_code == 200:
            return response.json()
        else:
            time.sleep(1)

# Executa o processo
def call_api():
    dados = buscar_clima(api_key)

    seed = generate_seed(dados)

    return seed