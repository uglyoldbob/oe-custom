#!/bin/sh


echo "Starting xbox qemu using compiled bios"
./i386-softmmu/qemu-system-i386 -cpu pentium3 -machine xbox,bootrom=./mcpx_1.0.bin -m 64 -usb -device usb-xbox-gamepad -bios /home/thomas/xbox2/build/workspace/sources/cromwell/image/cromwell.bin -s -S &

gdb --command=./xboxnewgdb
