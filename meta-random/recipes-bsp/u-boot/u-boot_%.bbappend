FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:xbox = " file://0001-Add-support-for-the-original-microsoft-xbox.patch"
SRC_URI:append:xjrad = " file://0001-Add-xjrad-support.patch"
