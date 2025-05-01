import Call_weather_api
import Call_random_api
#import call_another_api
#import call_third_api  
from password_generator import generate_password
import os
import merge_seeds

def clean_console():
    if os.name == "nt":
        os.system("cls")
    else:
        os.system("clear")

# Set traceability level (controls how many hash sources to collect)
traceability_level = 2

# List of API functions with names
api_functions = [
    ("Weather API", Call_weather_api.call_api),   # API 1
    ("Random.org API", Call_random_api.call_api), # API 2
    # ("Third API", call_third_api.main),     # Exemplo fictício
]

# Collect hashes dynamically based on traceability level
hashes = []
for i in range(traceability_level):
    api_index = i % len(api_functions)
    api_name, api_func = api_functions[api_index]

    print(f"{api_name} foi chamado.")  # Log do nome da API
    hash_value = api_func()
    hashes.append((api_name, hash_value))  # Associa nome e hash

# Merge all available hashes
final_hash = merge_seeds.merge_seeds(*hashes)

# Clear console and display results
clean_console()
print("Final hash:", final_hash)

# Generate password using the final hash as seed
password = generate_password(final_hash, 10, False, True, False, True)
print(f"Generated password: {password}")
