module Paths_lyah (
    version,
    getBinDir, getLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
catchIO = Exception.catch


version :: Version
version = Version {versionBranch = [0,1,0,0], versionTags = []}
bindir, libdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "/Users/bmiller/Library/Haskell/bin"
libdir     = "/Users/bmiller/Library/Haskell/ghc-7.8.3-x86_64/lib/lyah-0.1.0.0"
datadir    = "/Users/bmiller/Library/Haskell/share/ghc-7.8.3-x86_64/lyah-0.1.0.0"
libexecdir = "/Users/bmiller/Library/Haskell/libexec"
sysconfdir = "/Users/bmiller/Library/Haskell/etc"

getBinDir, getLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "lyah_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "lyah_libdir") (\_ -> return libdir)
getDataDir = catchIO (getEnv "lyah_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "lyah_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "lyah_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
