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

bindir     = "/home/bm3719/.cabal/bin"
libdir     = "/home/bm3719/.cabal/lib/x86_64-freebsd-ghc-7.6.3/lyah-0.1.0.0"
datadir    = "/home/bm3719/.cabal/share/x86_64-freebsd-ghc-7.6.3/lyah-0.1.0.0"
libexecdir = "/home/bm3719/.cabal/libexec"
sysconfdir = "/home/bm3719/.cabal/etc"

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
