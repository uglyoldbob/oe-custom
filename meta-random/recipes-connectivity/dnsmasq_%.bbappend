SYSTEMD_SERVICE:${PN} = ""

do_install:append() {
	rm -v ${D}/usr/lib/systemd/system/dnsmasq.service
	rm -rf ${D}/usr/lib/systemd/system
	rm -rf ${D}/usr/lib/systemd
	rm -rf ${D}/usr/lib
}
