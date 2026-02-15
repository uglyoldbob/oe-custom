FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RDEPENDS:${PN}:append = " adwaita-icon-theme adwaita-icon-theme-cursors"

# [Shell] is already uncommented by default in Variscite's weston.ini
INI_UNCOMMENT_ASSIGNMENTS:remove:mx8mq-nxp-bsp = " \
    \\[shell\\] \
"

INI_UNCOMMENT_ASSIGNMENTS:append:mx6-nxp-bsp = " \
    use-g2d=1 \
"

PACKAGECONFIG:append:imx91-var-som = " use-pixman"

WATCHDOG_SEC = "40"

# same as update_file in meta-imx, but warn instead of error
update_file_weak() {
    if ! grep -q "$1" $3; then
        # Warn if fails
        bbwarn $1 not found in $3
    else
        sed -i -e "s,$1,$2," $3
    fi
}

do_install:append() {
    update_file_weak "ExecStart=/usr/bin/weston " "ExecStart=/usr/bin/weston --shell=kiosk-shell.so " ${D}${systemd_system_unitdir}/weston.service
}
