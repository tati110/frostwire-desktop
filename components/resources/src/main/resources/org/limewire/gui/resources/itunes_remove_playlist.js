var iTunesApp = WScript.CreateObject("iTunes.Application");

if (iTunesApp == undefined)
{
    WScript.Quit();
}

var args = WScript.Arguments;

var playlistFrostWireName;

playlistFrostWireName = args(0);

var mainLibrary = iTunesApp.LibrarySource;
var playlists = mainLibrary.Playlists;
var numPlaylists = playlists.Count;

var i;
var playlistFrostWire;

playlistFrostWire = null;

for (i = 1; i <= numPlaylists; i++) {
    var	playlist = playlists.Item(i);
    if (playlist.Name == playlistFrostWireName)
    {
        playlistFrostWire = playlist;
		break;
    }
}

if (playlistFrostWire != null) {
    playlistFrostWire.Delete();
}