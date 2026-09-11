# cloud-itonami-municipality-chn-shanghai

Municipal-ordinance compliance catalog for **Shanghai** (上海市) — the
second CHN member of the `cloud-itonami-municipality-*` compliance-fact
family of ADR-2607141700 (`cloud-itonami-compliance-fact-federation`, in
`com-junkawasaki/root`), after
[`cloud-itonami-municipality-chn-beijing`](https://github.com/cloud-itonami/cloud-itonami-municipality-chn-beijing).

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on Shanghai
Municipality's behalf, and it is not the municipality.

Coverage is reported honestly: a municipality not in `catalog` has **no
spec-basis**, full stop — never fabricate one.

## Data

- `src/ordinance/facts.cljk` — 2 ordinance entries, source of truth.
- `src/culture/facts.cljk` — 6 culture entries, source of truth.
- `schema/ordinance.edn` / `schema/culture.edn` — DataScript schemas,
  **deliberately identical to every sibling**; a test asserts every
  attribute the catalogs use is declared, so a divergence cannot slip in.
- `data/datascript-tx.edn` / `data/culture-tx.edn` — derived tx-data,
  generated from the catalogs (tests assert they match).

## What was verified

Both ordinance entries were read on **2026-07-27** from
`cgzf.sh.gov.cn` (上海市城市管理行政执法局):

| Entry | How it was read | What the text states |
|---|---|---|
| 上海市生活垃圾管理条例 | official **PDF** downloaded, first and last pages read | header: 「（2019年1月31日上海市第十五届人民代表大会第二次会议通过）」; 第六十五条: 「本条例自2019年7月1日起施行。」 |
| 上海市公共场所控制吸烟条例 | HTML page, header line + 第一条 read | 通过 2009-12-10 (十三届人大常委会第十五次会議); 第一次修正 2016-11-11; 第二次修正 2022-10-28; 第一条 names 电子烟 within scope |

**Differences from the Beijing sibling, deliberately preserved:**

- **Both entries here are the consolidated ordinance**, not an amendment
  decision. Beijing's waste entry is an amendment decision because that is
  the page that was actually readable there; Shanghai's PDF gave the whole
  text, so this one does not carry that caveat. A test asserts both are
  `:kind :ordinance` and that the coverage note says so.
- **The waste ordinance was passed by the FULL congress**
  (第十五届人民代表大会第二次会议), not its Standing Committee. A metadata
  block elsewhere on the site lists 发文机构 as the Standing Committee; the
  ordinance's own header line is what is recorded, and a test asserts the
  full-congress wording survives.
- **`:e-cigarettes` is in the smoking entry's topics because 第一条 says
  so**, not because it was inferred from the ordinance's age.

**Two honest absences:**

1. **No `:performing-art` entry.** Huju (沪剧), the obvious
   Shanghainese-opera candidate, returned HTTP 404 at the URL tried, and
   nothing is recorded from a page that was not read. Beijing's catalog
   has one; Shanghai's does not yet. A test asserts it stays empty.
2. **Xiaolongbao and shengjian mantou name their other origin.** The
   sources tie them to Changzhou and Suzhou respectively as well as
   Shanghai, and the summaries say exactly that rather than presenting
   them as purely Shanghainese. A test asserts both summaries still name
   the other place.

## License

AGPL-3.0-or-later. Ordinance text itself remains the municipality's; this
repo stores only citation metadata.

## Running it

`kbb -M:test` (17 tests, 47 assertions) and `kbb -M:lint`.
