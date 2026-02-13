#!/bin/sh


echo "Starting xbox qemu using u-boot"
#truncate -s 1048576 /home/thomas/xbox2/build/tmp/work/xbox-poky-linux/u-boot/1_2018.01-r0/u-boot-2018.01/u-boot.bin
./i386-softmmu/qemu-system-i386 -cpu pentium3 -machine xbox,bootrom=./mcpx_1.0.bin -m 64 -usb -device usb-xbox-gamepad -bios /home/thomas/xbox2/build/tmp/work/xbox-poky-linux/u-boot/1_2018.01-r0/u-boot-2018.01/u-boot.bin -s -S &

gdb --command=./xboxubootgdb
