This is a sample of the android Storage Access Framework, but it currently fails to support rename on Google Docs Uri's.

* The Floating Action Button will launch the storage access framework UI
* The Options menu has save support (only works after opening file)
* The Options menu has a "rename" item, but it crashes when using a google doc file (only runs after opening file)

The rename button will work when using files on internal storage.

To reproduce the error:

1. Install this app and google drive/docs app
1. Open SafExample
1. Hit the Floating Action Button to open Storage Access Framework looking for text files
1. Open a txt file
1. Click "Rename" in the options menu once the txt file loads

At this point it crashes.  Here is the error: 

```
03-03 14:10:41.529 29069-29069/com.example.safexample W/grok: java.io.FileNotFoundException
                                                                  at android.database.DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(DatabaseUtils.java:144)
                                                                  at android.content.ContentProviderProxy.openTypedAssetFile(ContentProviderNative.java:692)
                                                                  at android.content.ContentResolver.openTypedAssetFileDescriptor(ContentResolver.java:1103)
                                                                  at android.content.ContentResolver.openAssetFileDescriptor(ContentResolver.java:942)
                                                                  at android.content.ContentResolver.openInputStream(ContentResolver.java:662)
                                                                  at com.example.safexample.SafDocument$2.openStream(SafDocument.java:66)
                                                                  at com.google.common.io.ByteSource$AsCharSource.openStream(ByteSource.java:420)
                                                                  at com.google.common.io.CharSource.read(CharSource.java:147)
                                                                  at com.example.safexample.SafDocument.read(SafDocument.java:73)
                                                                  at com.example.safexample.MainActivity.onOptionsItemSelected(MainActivity.java:61)
                                                                  at android.app.Activity.onMenuItemSelected(Activity.java:2912)
                                                                  at android.support.v4.app.FragmentActivity.onMenuItemSelected(FragmentActivity.java:404)
                                                                  at android.support.v7.app.AppCompatActivity.onMenuItemSelected(AppCompatActivity.java:167)
                                                                  at android.support.v7.view.WindowCallbackWrapper.onMenuItemSelected(WindowCallbackWrapper.java:100)
                                                                  at android.support.v7.view.WindowCallbackWrapper.onMenuItemSelected(WindowCallbackWrapper.java:100)
                                                                  at android.support.v7.app.ToolbarActionBar$2.onMenuItemClick(ToolbarActionBar.java:69)
                                                                  at android.support.v7.widget.Toolbar$1.onMenuItemClick(Toolbar.java:169)
                                                                  at android.support.v7.widget.ActionMenuView$MenuBuilderCallback.onMenuItemSelected(ActionMenuView.java:760)
                                                                  at android.support.v7.view.menu.MenuBuilder.dispatchMenuItemSelected(MenuBuilder.java:811)
                                                                  at android.support.v7.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:152)
                                                                  at android.support.v7.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:958)
                                                                  at android.support.v7.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:948)
                                                                  at android.support.v7.widget.ActionMenuView.invokeItem(ActionMenuView.java:618)
                                                                  at android.support.v7.view.menu.ActionMenuItemView.onClick(ActionMenuItemView.java:139)
                                                                  at android.view.View.performClick(View.java:5201)
                                                                  at android.view.View$PerformClick.run(View.java:21163)
                                                                  at android.os.Handler.handleCallback(Handler.java:746)
                                                                  at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                  at android.os.Looper.loop(Looper.java:148)
                                                                  at android.app.ActivityThread.main(ActivityThread.java:5443)
                                                                  at java.lang.reflect.Method.invoke(Native Method)
                                                                  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:728)
                                                                  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:618)
03-03 14:10:41.535 29069-29069/com.example.safexample D/AndroidRuntime: Shutting down VM
03-03 14:10:41.538 29069-29069/com.example.safexample E/AndroidRuntime: FATAL EXCEPTION: main
                                                                        Process: com.example.safexample, PID: 29069
                                                                        java.lang.SecurityException: Permission Denial: reading com.google.android.apps.docs.storagebackend.StorageBackendContentProvider uri content://com.google.android.apps.docs.storage/document/renamed1457032241419.txt from pid=29069, uid=10054 requires android.permission.MANAGE_DOCUMENTS, or grantUriPermission()
                                                                            at android.os.Parcel.readException(Parcel.java:1599)
                                                                            at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:183)
                                                                            at android.database.DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(DatabaseUtils.java:146)
                                                                            at android.content.ContentProviderProxy.openTypedAssetFile(ContentProviderNative.java:692)
                                                                            at android.content.ContentResolver.openTypedAssetFileDescriptor(ContentResolver.java:1103)
                                                                            at android.content.ContentResolver.openAssetFileDescriptor(ContentResolver.java:942)
                                                                            at android.content.ContentResolver.openAssetFileDescriptor(ContentResolver.java:865)
                                                                            at com.example.safexample.SafDocument$3.openStream(SafDocument.java:88)
                                                                            at com.google.common.io.ByteSource$AsCharSource.openStream(ByteSource.java:420)
                                                                            at com.google.common.io.CharSource.read(CharSource.java:147)
                                                                            at com.example.safexample.SafDocument.tryAsFileDescriptor(SafDocument.java:91)
                                                                            at com.example.safexample.SafDocument.read(SafDocument.java:77)
                                                                            at com.example.safexample.MainActivity.onOptionsItemSelected(MainActivity.java:61)
                                                                            at android.app.Activity.onMenuItemSelected(Activity.java:2912)
                                                                            at android.support.v4.app.FragmentActivity.onMenuItemSelected(FragmentActivity.java:404)
                                                                            at android.support.v7.app.AppCompatActivity.onMenuItemSelected(AppCompatActivity.java:167)
                                                                            at android.support.v7.view.WindowCallbackWrapper.onMenuItemSelected(WindowCallbackWrapper.java:100)
                                                                            at android.support.v7.view.WindowCallbackWrapper.onMenuItemSelected(WindowCallbackWrapper.java:100)
                                                                            at android.support.v7.app.ToolbarActionBar$2.onMenuItemClick(ToolbarActionBar.java:69)
                                                                            at android.support.v7.widget.Toolbar$1.onMenuItemClick(Toolbar.java:169)
                                                                            at android.support.v7.widget.ActionMenuView$MenuBuilderCallback.onMenuItemSelected(ActionMenuView.java:760)
                                                                            at android.support.v7.view.menu.MenuBuilder.dispatchMenuItemSelected(MenuBuilder.java:811)
                                                                            at android.support.v7.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:152)
                                                                            at android.support.v7.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:958)
                                                                            at android.support.v7.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:948)
                                                                            at android.support.v7.widget.ActionMenuView.invokeItem(ActionMenuView.java:618)
                                                                            at android.support.v7.view.menu.ActionMenuItemView.onClick(ActionMenuItemView.java:139)
                                                                            at android.view.View.performClick(View.java:5201)
                                                                            at android.view.View$PerformClick.run(View.java:21163)
                                                                            at android.os.Handler.handleCallback(Handler.java:746)
                                                                            at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                            at android.os.Looper.loop(Looper.java:148)
                                                                            at android.app.ActivityThread.main(ActivityThread.java:5443)
                                                                            at java.lang.reflect.Method.invoke(Native Method)
                                                                            at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:728)
                                                                            at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:618)
```
