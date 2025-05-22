import Call_weather_api
import Call_random_api 
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
    ("Random.org API", Call_random_api.call_api), # API 1
    ("Weather API", Call_weather_api.call_api)    # API 2
]

# Collect hashes dynamically based on traceability level
hashes = []
for i in range(traceability_level):
    api_index = i % len(api_functions)
    api_name, api_func = api_functions[api_index]

    print(f"{api_name} foi chamado.")  # Log do nome da API
    hash_value = api_func()
    hashes.append((api_name, hash_value))  

# Merge all available hashes
final_hash = merge_seeds.merge_seeds(*hashes)

clean_console()
print("Final hash:", final_hash)

# Generate password using the final hash as seed
password = generate_password(final_hash, 10, True, True, True, True)
print(f"Generated password: {password}")
