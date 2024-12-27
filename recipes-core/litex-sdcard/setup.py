import setuptools

with open("opencores_sdcard/trunk/README.md", "r") as fh:
    long_description = fh.read()

version_str = "15"

setuptools.setup(
    name="pythondata-opencores-sdcard",
    version=version_str,
    author="Marek Czerski",
    author_email="ma.czerski@gmail.com",
    description="""\
Python module containing hdl files for wishbone slave for sdcard master.""",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="svn://opencores.org/ocsvn/sd_card_controller/sd_card_controller",
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: Unknown",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.5',
    zip_safe=False,
    packages=setuptools.find_packages(),
    package_data={
    	'opencores_sdcard': ['**'],
    },
    include_package_data=True,
)
