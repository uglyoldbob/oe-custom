FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:xjrad += "file://ethernet-static.nmconnection \
	file://ethernet-dhcp.nmconnection \
"

do_install:append() {
	install -d ${D}/etc/NetworkManager/system-connections
	install -d ${D}/usr/lib/NetworkManager/system-connections
	install -m 0600 ${WORKDIR}/sources-unpack/*.nmconnection ${D}/usr/lib/NetworkManager/system-connections/
}

