PACKAGECONFIG = "a2dpconf rfcomm aplay cli hcitop ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}"
