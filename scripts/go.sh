#!/bin/sh
echo "Running"
echo $1
set -e
bitbake $1
if [ "$2" != "net" ]
then
	wic create ../poky/meta-gumstix-extras/scripts/lib/wic/canned-wks/sdimage-gumstix.wks -e $1 | tee scanme
	IMAGE_NAME=`grep "can be found" ./scanme -A 1 | sed -n '2p'`
	echo $IMAGE_NAME
	echo "Creating image for sdcard"
	bmaptool create $IMAGE_NAME > image.bmap
	sudo bmaptool copy --bmap image.bmap $IMAGE_NAME $2
else
	echo "Copying files for network boot to " $3 $4
	sudo rsync -a --del --chown=root:root ./tmp/work/overo-poky-linux-gnueabi/$1/1.0-r0/rootfs/ $3
	sudo cp ./tmp/work/overo-poky-linux-gnueabi/$1/1.0-r0/rootfs/boot/zImage-* /var/lib/tftpboot/zImage$4
	sudo cp ./tmp/work/overo-poky-linux-gnueabi/$1/1.0-r0/rootfs/boot/devicetree-zImage-omap3-overo-chestnut43.dtb /var/lib/tftpboot/thing$4.dtb
fi
echo "Done"
