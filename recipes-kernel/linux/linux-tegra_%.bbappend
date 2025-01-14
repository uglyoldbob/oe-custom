FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = "file://xjrad-old;type=kmeta;destsuffix=xjrad  \
	file://0001-Add-device-tree-for-custom-hardware-based-on-jetson-.patch \
"

