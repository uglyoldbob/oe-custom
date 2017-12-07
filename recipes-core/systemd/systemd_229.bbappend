do_install_append() {
	# Make journal storage volatile only
	sed -i -e 's/.*Storage.*/Storage=volatile/' ${D}${sysconfdir}/systemd/journald.conf
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-hostnamed.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-journald.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-logind.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-networkd.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-timesyncd.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-timedated.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-resolved.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-machined.service
	sed -i -e 's/CapabilityBoundingSet/#CapabilityBoundingSet/' ${D}${systemd_unitdir}/system/systemd-localed.service
}

