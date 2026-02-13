load mmc 1 $loadaddr /boot/Image
load mmc 1 $fdt_addr_r /boot/xjrad.dtb
booti $loadaddr - $fdt_addr_r
