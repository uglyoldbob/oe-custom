#!/bin/sh

set -e

echo "XBOX ftp transfer script"

if [ "$#" -ne 3 ]; then
  echo "Wrong number of parameters"
  echo "Usage: $0 a b c"
  echo " a = ip address"
  echo " b = local filename"
  echo " c = xbox destination"
fi

FPATH=${3%/*}
echo "Destination Path is ${FPATH}"
FILE=${3##*/}
echo "Destination Filename is ${FILE}"

FPATHSRC=${2%/*}

if [ -z "${FPATHSRC}" ]; then
  echo "Blank path"
  FPATHSRC="."
  FILESRC="$2"
else
  echo "Not blank"
  FILESRC=${2##*/}  
fi
echo "Source path is ${FPATHSRC}"
echo "Source filename is ${FILESRC}"

rm -f ${FPATHSRC}/testfile

OUT=$(ftp -n $1 << END_SCRIPT
quote USER xbox
quote PASS xbox
lcd $FPATHSRC
cd $FPATH
binary
put "$FILESRC" "$FILE"
get "$FILE" testfile
quit
END_SCRIPT
)

CHECK1=$(md5sum $2 | awk '{print $1;}')
CHECK2=$(md5sum ${FPATHSRC}/testfile | awk '{print $1;}')
echo "$OUT"

echo "Output is $CHECK1 $CHECK2"

if [ "$CHECK1" == "$CHECK2" ]; then
	echo "File transfer is GOOD!"
else
	echo "File transfer failed"
	exit 1
fi
