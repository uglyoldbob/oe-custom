#!/bin/sh
echo "Running"
echo $1
set -e
QEMU_DEBUG="-s -S"
#QEMU_DEBUG=" "
bitbake $1
case "$2" in
 net )
  echo "Copying files for network boot to " $3 $4
  sudo rsync -a --del --chown=root:root ./tmp/work/overo-poky-linux-gnueabi/$1/1.0-r0/rootfs/ $3
  sudo cp ./tmp/work/overo-poky-linux-gnueabi/$1/1.0-r0/rootfs/boot/zImage-* /var/lib/tftpboot/zImage$4
  sudo cp ./tmp/work/overo-poky-linux-gnueabi/$1/1.0-r0/rootfs/boot/devicetree-zImage-omap3-overo-chestnut43.dtb /var/lib/tftpboot/thing$4.dtb
  ;;
 qemuflash )
  echo "Making flash image for qemu"
  rm flash
  dd of=flash bs=1k count=16k if=/dev/zero
  dd of=flash bs=1k conv=notrunc if=tmp/deploy/images/verdex/u-boot.bin
  dd of=flash bs=1k conv=notrunc seek=256 if=tmp/deploy/images/verdex/$1-verdex.jffs2
  dd of=flash bs=1k conv=notrunc seek=28672 if=tmp/deploy/images/verdex/uImage
  echo "Running qemu"
  qemu-system-arm -M verdex -pflash flash -monitor null -nographic -m 289 $QEMU_DEBUG
  ;;
 qemucf )
  rm flash
  dd of=flash bs=1k count=16k if=/dev/zero
  dd of=flash bs=1k conv=notrunc if=tmp/deploy/images/verdex/u-boot.bin
  dd of=flash bs=1k conv=notrunc seek=256 if=tmp/deploy/images/verdex/$1-verdex.jffs2
  dd of=flash bs=1k conv=notrunc seek=31744 if=tmp/deploy/images/verdex/uImage
  wic create ../poky/meta-uglyoldbob/scripts/lib/wic/canned-wks/sdimage-gumstix.wks -e $1 | tee scanme
  IMAGE_NAME=`grep "can be found" ./scanme -A 1 | sed -n '2p'`
  echo $IMAGE_NAME
  echo "Creating image for cf"
  bmaptool create $IMAGE_NAME > image.bmap
  bmaptool copy --bmap image.bmap $IMAGE_NAME ./cf_image.img
  qemu-system-arm -M verdex -pflash flash -hda cf_image.img -monitor null -nographic -m 289 $QEMU_DEBUG
  ;;
 * )	
  wic create ../poky/meta-uglyoldbob/scripts/lib/wic/canned-wks/sdimage-gumstix.wks -e $1 | tee scanme
  IMAGE_NAME=`grep "can be found" ./scanme -A 1 | sed -n '2p'`
  echo $IMAGE_NAME
  echo "Creating image for cf"
  bmaptool create $IMAGE_NAME > image.bmap
  sudo bmaptool copy --bmap image.bmap $IMAGE_NAME $2
  ;;
esac

echo "Done"
