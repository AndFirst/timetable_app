# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = 'Timetable'
copyright = '2023, Bartłomiej Ściseł'
author = 'Bartłomiej Ściseł'
release = '1.0'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

extensions = [
    'sphinxcontrib.plantuml',
    #     'myst_parser',
    'sphinxcontrib.jquery',
    'sphinx_needs'
]

plantuml = 'java -jar /home/user/jars/plantuml-1.2023.12.jar'

needs_types = [dict(directive="hlreq", title="High-level requirement", prefix="HLR_", color="#BFD8D2", style="node"),
               dict(directive="llreq", title="Low-level requirement", prefix="LLR_", color="#FEDCD2", style="node"),
               dict(directive="nfreq", title="Non-functional requirement", prefix="NFR_", color="#FEDCD2", style="node"),
               dict(directive="mts", title="Manual test specification", prefix="MTS_", color="#FEDCD2", style="node"),
               # Kept for backwards compatibility
               dict(directive="need", title="Need", prefix="N_", color="#9856a5", style="node")
               ]
needs_id_length = 3

needs_extra_links = [
   {
      "option": "specifies",
      "incoming": "is specified by",
      "outgoing": "specifies",
   },
   {
      "option": "requires",
      "incoming": "is required by",
      "outgoing": "requires",
   }
]


templates_path = ['_templates']
exclude_patterns = []

language = 'en'

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

html_theme = 'alabaster'
html_static_path = ['_static']

needs_build_json = True