try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

config = {
    'description': 'My Project',
    'author': 'Bruce C. Miller',
    'url': 'URL to get it at.',
    'download_url': 'Where to download it.',
    'author_email': 'bm3719@gmail.com',
    'version': '1.0',
    'install_requires', ['nose'],
    'packages': ['NAME'],
    'scripts': [],
    'name': 'projectname'
    }

setup(**config)
