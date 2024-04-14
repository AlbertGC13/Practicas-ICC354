<!DOCTYPE html>
<html class="h-full bg-white" lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <!-- Tailwindcss CDN -->
  <script src="https://cdn.tailwindcss.com"></script>

  <!-- AlpineJS CDN -->
  <script defer src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js"></script>

  <!-- Inter Font -->
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
    rel="stylesheet" />
  <style>
    * {
font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont,
'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans',
'Helvetica Neue', sans-serif;
}
  </style>

  <title>Mockly PUCMM - Clientes</title>
</head>

<body class="h-full">
  <div class="min-h-full">
    <div class="pb-32 bg-white">
      <!-- Navigation -->
      <nav class="border-b border-opacity-25 border-grey-300 bg-white"
        x-data="{ mobileMenuOpen: false, userMenuOpen: false }">
        <div class="px-4 mx-auto max-w-7xl sm:px-6 lg:px-8">
          <div class="flex justify-between h-16">
            <div class="flex items-center px-2 lg:px-0">
              <div class="hidden sm:block">
                <div class="flex space-x-4">

                  <a href="/admin/" class="px-3 py-2 text-sm font-medium text-white rounded-md bg-black">Clientes</a>
                  <a href="/admin/mocks"
                    class="px-3 py-2 border border-white text-sm font-medium text-black rounded-md">Mocks</a>
                </div>
              </div>
            </div>
            <div class="hidden gap-2 sm:ml-6 sm:flex sm:items-center">
              <div class="relative ml-3" x-data="{ open: false }">
                <div>
                  <button @click="open = !open" type="button"
                    class="flex text-sm bg-white rounded-full focus:outline-none" id="user-menu-button"
                    aria-expanded="false" aria-haspopup="true">
                    <span class="sr-only">Open user menu</span>
                    <span class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-black">
                      <span class="font-medium leading-none text-white">${logged.getNombre()?substring(0,
1)}${logged.getApellido()?substring(0, 1)}</span>
                    </span>

                  </button>
                </div>

                <!-- Dropdown menu -->
                <div x-show="open" @click.away="open = false"
                  class="absolute right-0 z-10 w-48 py-1 mt-2 origin-top-right bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
                  role="menu" aria-orientation="vertical" aria-labelledby="user-menu-button" tabindex="-1">
                  <a href="/logout" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100" role="menuitem"
                    tabindex="-1" id="user-menu-item-2">Cerrar Sesion</a>
                </div>
              </div>
            </div>
            <div class="flex items-center -mr-2 sm:hidden">
              <!-- Mobile menu button -->
              <button @click="mobileMenuOpen = !mobileMenuOpen" type="button"
                class="inline-flex items-center justify-center p-2 rounded-md text-sky-100 hover:bg-sky-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-sky-500"
                aria-controls="mobile-menu" aria-expanded="false">
                <span class="sr-only">Open main menu</span>
                <!-- Icon when menu is closed -->
                <svg x-show="!mobileMenuOpen" class="block w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                  viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                  <path stroke-linecap="round" stroke-linejoin="round"
                    d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>

                <!-- Icon when menu is open -->
                <svg x-show="mobileMenuOpen" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                  stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
          </div>
        </div>
        <div x-show="mobileMenuOpen" class="sm:hidden" id="mobile-menu">
          <div class="pt-2 pb-3 space-y-1">
            <a href="/admin/"
              class="block px-3 py-2 text-base font-medium text-white rounded-md bg-sky-700">Usuarios</a>
            <a href="/admin/mocks"
              class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-sky-500 hover:bg-opacity-75">Mocks</a>
          </div>
          <div class="pt-4 pb-3 border-t border-sky-700">
            <div class="flex items-center px-5">
              <div class="flex-shrink-0">
                <span class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-sky-100">
                  <span class="font-medium leading-none text-sky-700">${logged.getNombre()?substring(0,
1)}${logged.getApellido()?substring(0, 1)}</span>
                </span>
              </div>
              <div class="ml-3">
                <div class="text-base font-medium text-white">
                   ${logged.getApellido()}
                </div>
                <div class="text-sm font-medium text-sky-300">
                  ${logged.getUsername()}
                </div>
              </div>
            </div>
            <div class="px-2 mt-3 space-y-1">
              <a href="/logout"
                class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-sky-500 hover:bg-opacity-75">Cerrar
                Sesion</a>
            </div>
          </div>
        </div>
      </nav>

      <header class="py-10">
        <div class="px-4 mx-auto max-w-7xl sm:px-6 lg:px-8">
          <h1 class="text-3xl font-bold tracking-tight text-black">
            Clientes de Mockly
          </h1>
        </div>
      </header>
    </div>

    <main class="-mt-32">
      <div class="px-2 pb-12 mx-auto max-w-7xl sm:px-6 lg:px-8">
        <div class="py-6">
          <div class="px-4 sm:px-6 lg:px-8">
            <div class="sm:flex sm:items-center">
              <div class="sm:flex-auto">

              </div>
              <div class="mt-4 sm:ml-16 sm:mt-0 sm:flex-none">
                <a href="/admin/usuario" type="button"
                  class="block px-3 py-2 text-sm font-semibold text-center text-white rounded-md shadow-sm bg-black hover:bg-black-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-black-600">
                  Nuevo Cliente
                </a>
              </div>
            </div>

            <div class="flow-root mt-8">
              <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
                <ul id="paginated-list" aria-live="polite" role="list" class="divide-y divide-gray-100">
                  <#list usuarios as user>
                    <li class="relative flex justify-between px-4 py-5 gap-x-6 sm:px-6 lg:px-8">
                      <div class="flex gap-x-4">


                        <div class="flex-auto min-w-0">
                          <p class="text-sm font-semibold leading-6 text-gray-900">
                            <a href="/admin/mocks?id=${user.getId()}">
                              <span class="absolute inset-x-0 bottom-0 -top-px"></span>
                              Cliente: ${user.getApellido()}
                            </a>
                          </p>
                          <p class="flex mt-1 text-xs leading-5 text-gray-500">
                            <a href="/admin/mocks?id=${user.getId()}" class="relative truncate hover:underline">
                              Usuario: ${user.getUsername()}
                            </a>
                          </p>
                        </div>
                      </div>
                      <div class="flex flex-col md:flex-row md:justify-center">
                        <a href="/admin/usuario?id=${user.getId()}" type="button"
                          class="relative truncate text-white bg-black hover:bg-black-500 focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">
                          Editar
                        </a>
                        <#if user.isActivo()>
                          <a href="/admin/borrarUsuario/${user.getId()}"
                            onclick="return confirm('Estas apunto de bloquear este usuario..');" type="button"
                            class="relative truncate text-white bg-orange-700 hover:bg-orange-800 focus:ring-4 focus:ring-orange-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">
                            Bloquear
                          </a>
                          <#else>
                            <a href="/admin/borrarUsuario/${user.getId()}"
                              onclick="return confirm('Estas apunto de activar este usuario...');" type="button"
                              class="relative truncate text-white bg-green-600 hover:bg-green-500 focus:ring-50 focus:ring-amber-green font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">
                              Desbloquear
                            </a>
                        </#if>
                      </div>
                    </li>
                  </#list>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</body>
<script src="https://unpkg.com/@material-tailwind/html@latest/scripts/dismissible.js"></script>

</html>