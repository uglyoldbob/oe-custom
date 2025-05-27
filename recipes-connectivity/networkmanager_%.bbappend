FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://ethernet-static.nmconnection \
	file://NetworkManager.conf \
"

do_install:append() {
	install -d ${D}/etc/NetworkManager/system-connections
	install -m 0600 ${UNPACKDIR}/NetworkManager.conf ${D}/etc/NetworkManager/NetworkManager.conf
	install -d ${D}/usr/lib/NetworkManager/system-connections
	for file in ${WORKDIR}/sources-unpack/*.nmconnection; do
		if [ -f "$file" ]; then
			install -m 0600 "$file" ${D}/usr/lib/NetworkManager/system-connections/
		fi
	done
}

