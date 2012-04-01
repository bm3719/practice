module Ch5 where

import IO

-- bracket: Used like try/finally for IO.  This takes an initial function
-- (opening a file), the operation to perform at the end whether a failure
-- occurs of not (closing a file), and finally the operation to perform.
writeChar :: FilePath -> Char -> IO ()
writeChar fp c =
  bracket
  (openFile fp ReadMode)
  hClose
  (\h -> hPutChar h c)
