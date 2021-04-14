package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.mywebbrowser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 웹뷰 기본 설정
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    binding.urlEditText.setText(url)
                }
            }
        }

        binding.webView.loadUrl("https://www.google.com")

        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            } else {
                false
            }
        }

        // 컨텍스트 메뉴 등록
        registerForContextMenu(binding.webView)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // 옵션 메뉴 작성
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_google, R.id.action_home -> {
                binding.webView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver -> {
                binding.webView.loadUrl("https://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                binding.webView.loadUrl("https://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                binding.webView.url?.let { url ->
                    sendSMS("031-123-4567", url)
                }
                return true
            }
            R.id.action_email -> {
                binding.webView.url?.let { url ->
                    email("test@example.com", "좋은 사이트", url)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 컨텍스트 메뉴 작성
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                // 페이지 공유
                binding.webView.url?.let { url ->
                    share(url)
                }
                return true
            }
            R.id.action_browser -> {
                // 기본 웹 브라우저에서 열기
                binding.webView.url?.let { url ->
                    browse(url)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}