import hashlib
import random
import string

def generate_password(seed_64: str, size: int, upper_letters: bool, lower_letters: bool, digits: bool, special_characters: bool) -> str:
    if len(seed_64) != 64:
        raise ValueError("A seed precisa ter exatamente 64 caracteres.")

    # Converte a seed em um número inteiro fixo
    seed_int = int(hashlib.sha256(seed_64.encode()).hexdigest(), 16)

    # Cria um gerador de números aleatórios com base na seed
    rng = random.Random(seed_int)

    # Lista de grupos de caracteres e os tipos ativados
    grupos = []
    if upper_letters:
        grupos.append(string.ascii_uppercase)
    if lower_letters:
        grupos.append(string.ascii_lowercase)
    if digits:
        grupos.append(string.digits)
    if special_characters:
        grupos.append("!@#$%&*?.,")

    if not grupos:
        raise ValueError("É necessário ativar pelo menos um tipo de caractere para gerar a senha.")

    # Garante pelo menos um caractere de cada grupo
    senha = [rng.choice(grupo) for grupo in grupos]

    # Concatena todos os grupos para uso geral
    alfabet = ''.join(grupos)

    # Preenche o restante da senha com caracteres aleatórios
    restante = size - len(senha)
    senha += [rng.choice(alfabet) for _ in range(restante)]

    # Embaralha a senha final com a mesma seed para manter determinismo
    rng.shuffle(senha)

    return ''.join(senha)
