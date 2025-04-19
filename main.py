import random
import requests
import time
import base64
import pickle
import Call_weather_api
#import call_another_api
#import call_third_api  # Fictícia para mostrar o exemplo
import password_generator
import os
import merge_seeds

def clean_console():
    if os.name == "nt": os.system("cls")
    else: os.system("clear")

# Set traceability level (controls how many hash sources to collect)
traceability_level = 1  # Exemplo com 5 APIs chamadas

# List of API functions (add more APIs here in the future)
phone_data = [
    #memory data
]

api_functions = [
    Call_weather_api.main#,  # API 1
    #call_another_api.main,  # API 2
    #call_third_api.main     # API 3 (exemplo fictício)
    # Add more APIs if needed
]

# Collect hashes dynamically based on traceability level
hashes = []
for i in range(traceability_level):
    api_index = i % len(api_functions)  # Ensure we cycle through available APIs
    hash_value = api_functions[api_index]()  # Call the selected API
    hashes.append(hash_value)

# Merge all available hashes
final_hash = merge_seeds.merge_seeds(*hashes)

# Clear console and display results
clean_console()
print("Final hash:", final_hash)

# Generate password using the final hash as seed
password = password_generator.generate_password(final_hash, 100, True, True, True, True)
print(f"Generated password: {password}")
