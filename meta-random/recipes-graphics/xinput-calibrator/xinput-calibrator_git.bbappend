
do_install:append () {
  rm -rfv ${D}${sysconfdir}/X11/Xsession.d/30xinput_calibrate.sh
  rm -rfv ${D}${sysconfdir}/xdg/autostart/xinput_calibrator.desktop
}

