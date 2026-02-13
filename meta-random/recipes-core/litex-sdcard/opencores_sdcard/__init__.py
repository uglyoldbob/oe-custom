import os.path
__dir__ = os.path.split(os.path.abspath(os.path.realpath(__file__)))[0]
data_location = __dir__

class SdCard:
    def __init__(self, platform):
        data_location2 = os.path.join(data_location, "trunk", "rtl")
        loc1 = os.path.join(data_location2, "VHDL")
        for source in os.listdir(loc1):
                platform.add_source(os.path.join(loc1, source))
        loc2 = os.path.join(data_location2, "verilog")
        for source in os.listdir(loc2):
                platform.add_source(os.path.join(loc2, source))

