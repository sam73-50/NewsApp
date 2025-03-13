package com.newsappwithrssfeed.data.dataclassobj

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RSSFeed(
    @field:Element(name = "channel")
    var channel: Channel
){
    constructor() : this(Channel())
}

@Root(name = "channel", strict = false)
data class Channel(
    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Path("channel")
    @field:Element(name = "link", required = false)
    var channelLink: String? = null,

    @field:Path("channel")
    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "atom:link", required = false)
    @Namespace(reference = "http://www.w3.org/2005/Atom")
    var atomLink: String? = null,

    @field:ElementList(entry = "item", inline = true, required = false)
    var items: MutableList<NewsItem>? = mutableListOf()
){
    constructor() : this(null, null, String.toString())
}

@Root(name = "item", strict = false)
data class NewsItem(
    @field:Element(name = "title", required = false)
    var title: String = "",

    @field:Path("item")
    @field:Element(name = "link", required = false)
    var itemLink: String = "",

    @field:Element(name = "atom:link", required = false)
    @Namespace(reference = "http://www.w3.org/2005/Atom")
    var atomLinkItem: String? = null,

    @field:Path("item")
    @field:Element(name = "description", required = false)
    var itemDescription: String? = null,

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null,

    @field:Element(name = "guid", required = false)
    var guid: String? = null,

    @field:Element(name = "creator", required = false)
    var creator: String? = null,

    @field:Element(name = "content", required = false)
    var mediaContent: MediaContent? = null,

    @field:ElementList(inline = true, entry = "category", required = false)
    var categories: MutableList<String>? = mutableListOf()
){
    constructor() : this("", "", null, null, null)
}

@Root(name = "content", strict = false)
data class MediaContent(
    @field:Attribute(name = "url", required = false)
    var url: String? = null,

    @field:Attribute(name = "height", required = false)
    var height: String? = null,

    @field:Attribute(name = "width", required = false)
    var width: String? = null
)