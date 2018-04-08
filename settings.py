import os
from envparse import env

def get_env(name):
    if (os.path.exists('./env')):
        env.read_envfile('./env')
    return env(name)
