try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

config = {
    'description': 'ex49',
    'author': 'Bruce C. Miller',
    'url': '',
    'download_url': '',
    'author_email': 'bm3719@gmail.com',
    'version': '1.0',
    'install_requires': ['nose'],
    'packages': ['ex49'],
    'scripts': [],
    'name': 'ex49'
    }

setup(**config)
